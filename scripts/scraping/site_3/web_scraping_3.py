#!/usr/bin/python3

import json
import os
from pathlib import Path
from selenium.common.exceptions import TimeoutException, NoSuchElementException
import time
from datetime import date

import requests
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.options import Options


class Scraper:
    DT = date.today().strftime("%d/%m/%Y")
    HEADERS = {
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:55.0) Gecko/20100101 Firefox/55.0",
    }
    ROOT_URL = "https://caledoniastore.com.br"

    def navigate_beetween_pages(self):
        result = requests.get(self.ROOT_URL, headers=self.HEADERS)
        doc = BeautifulSoup(result.text, "html.parser")

        paginas = doc.find_all(class_="category_grid_box")

        for pagina in paginas:
            pagina = pagina.find("a")
            if pagina["href"] != f"{self.ROOT_URL}/categoria/coquetelaria/":
                self.navigate_page(pagina["href"])

    def navigate_page(self, url):
        option = Options()
        # faz com que o navegador não abra, faz tudo em background
        option.headless = True

        try:
            driver_path = Path("drivers", "chromedriver")
            driver = webdriver.Chrome(driver_path, options=option)
            driver.set_page_load_timeout(30)
            print(f"Opening {url}")
            driver.get(url)

            element = driver.find_element_by_class_name("getbowtied_ajax_load_button")
            element = element.find_element_by_css_selector("a")
            element.click()
            # WebDriverWait(driver, 10).until(
            #     EC.NoSuchElementException((By.CLASS_NAME, 'getbowtied_ajax_load_button')))
            time.sleep(5)

            result = driver.page_source
            doc = BeautifulSoup(result, "html.parser")
            products_page = doc.find(class_="products columns-6")
            products_page = products_page.find_all("li")

            for link in products_page:
                link = link.find("a")
                self.scrape(link["href"])
                print(link["href"])
        except TimeoutException:
            print(f"Timeout na url: {url}")
            return
        except NoSuchElementException:
            print(f"Elemento não encontrado na url: {url}")
            return
        finally:
            driver.quit()

    def scrape(self, url):
        result = requests.get(url, headers=self.HEADERS)
        doc = BeautifulSoup(result.text, "html.parser")

        # titulo
        title = doc.find("div", {"class": "product_summary_middle"})
        title = title.text.replace("/", "").replace(r"\𝟯𝟰𝟮\𝟮𝟬𝟬\𝟮𝟯𝟭𝘀", "")
        title = "".join(title.split("\n"))
        title = "".join(title.rstrip())
        print(title)

        # informações
        price = doc.find("span", {"class": "woocommerce-Price-amount amount"})
        if price:
            price = price.text.strip()
        else:
            price = ""

        markers = doc.find("div", {"class": "woocommerce-product-details__short-description"})

        if markers:
            markers = markers.get_text(strip=True, separator="\n").splitlines()
            markers.pop(0)
            for i, marker in enumerate(markers):
                markers[i] = map(lambda x: x.strip(), marker.split(":"))

            markers.insert(0, ["Preço", price])
            markers = tuple(markers)
            # print(tuple(markers))
            # converting into dict
            markers = dict(markers)
            print(markers)
        else:
            markers = {}

        description = doc.find("div", {"id": "tab-description"})
        if description:
            description = description.p.text.strip()
        else:
            description = ""

        json_infos = {"Nome": title, "Descrição": description, "Data": self.DT}
        json_infos.update(markers)
        print(json_infos)

        js = json.dumps(json_infos, indent=4, sort_keys=True, ensure_ascii=False)

        output_path = Path("saidas", f"{title}.json")
        output_path.parent.mkdir(exist_ok=True)
        with open(output_path, "w", encoding="utf-8") as f:
            f.write(js)


def main():
    scraper = Scraper()
    scraper.navigate_beetween_pages()


if __name__ == "__main__":
    main()
