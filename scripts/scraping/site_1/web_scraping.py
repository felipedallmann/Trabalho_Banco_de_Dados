#!/usr/bin/python3

import re
import json
from datetime import date
from pprint import pprint
from pathlib import Path

import requests
from bs4 import BeautifulSoup


class Scraper:
    DT = date.today().strftime("%d/%m/%Y")
    ROOT_URL = "https://www.casadabebida.com.br/whisky"

    def navigate_beetween_pages(self):
        result = requests.get(self.ROOT_URL)
        doc = BeautifulSoup(result.text, "html.parser")

        next_url = doc.find("a", attrs={"title": "Proxima Página"})
        print(next_url.parent.has_attr("class"))
        if next_url.get("href", None) is not None:
            self.navigate_page(next_url["href"])

        while not next_url.parent.has_attr("class"):
            url = next_url.get("href", None)
            result = requests.get(url)
            doc = BeautifulSoup(result.text, "html.parser")
            next_url = doc.find("a", attrs={"title": "Proxima Página"})
            if next_url.get("href", None) is not None:
                self.navigate_page(next_url["href"])

    def navigate_page(self, url):
        result = requests.get(url)
        doc = BeautifulSoup(result.text, "html.parser")

        products_page = doc.find(class_="row m-0 px-0")
        products = products_page.find_all(
            class_="col-6 col-md-6 col-lg-4 px-0 px-md-2 mb-5 produtos-categoria-thumb"
        )

        link = products[0]
        link = link.find("a")
        print(link["href"])

        for link in products:
            link = link.find("a")
            if link.get("href", None) is not None:
                print(link["href"])
                self.scrape(link["href"])
                print()

    def scrape(self, url):
        result = requests.get(url)
        doc = BeautifulSoup(result.text, "html.parser")

        # titulo
        title = doc.find_all("span", itemprop="name")
        title = title[-1].string.replace(r"\𝟯𝟰𝟮\𝟮𝟬𝟬\𝟮𝟯𝟭𝘀", "").replace("/", "")

        # informações
        product_id = doc.find("input", {"name": "productCode"}).get("value").strip()
        manufacturer = doc.find("input", {"name": "productManufacturer"}).get("value").strip()
        category = doc.find("input", {"name": "productCategory"}).get("value").strip()
        price = doc.find("input", {"name": "productPrice"}).get("value").strip()
        price_without_discount = doc.find("input", {"name": "productPricesOf"}).get("value").strip()

        # Limpa os preços
        replaces = [("R$", "")]
        for (old, new) in replaces:
            price_without_discount = price_without_discount.replace(old, new).strip()
            price = price.replace(old, new).strip()

        # descrição
        description = doc.find("div", {"class": "produto-descricao"})
        text = description.find_all("p")
        final_text = ""
        for aux_text in text:
            final_text = final_text + aux_text.text

        final_text = "".join(final_text.split("\t"))
        final_text = "".join(final_text.split("\n")).strip()

        # markers
        markers = description.find_all("li")
        alcohol_percent = "0"
        origin = ""
        list_of_markers = []
        for marker in markers:
            marker = marker.text
            marker = "".join(marker.split("\t"))
            marker = "".join(marker.split("\n")).strip()

            search_alcohol_percent = re.search(
                r"(?:alcoólica|alcoólico).*([0-9]{2}(?:(?:,|\.)[0-9]{1,3})?)%",
                marker,
                re.IGNORECASE,
            )
            search_origin = re.search(r"(?:importado da|origem):? (.*)", marker, re.IGNORECASE)

            if search_alcohol_percent:
                alcohol_percent = search_alcohol_percent.group(1).replace(",", ".").strip()
            elif search_origin:
                origin = search_origin.group(1).replace(".", "").replace('"', "").strip()
                if origin.lower() == "nacional":
                    origin = "Brasil"
            elif marker != "":
                list_of_markers.append(marker)

        json_infos = {
            "Nome": title,
            "Descrição": final_text,
            "Teor alcoólico": alcohol_percent,
            "Marcadores": list_of_markers,
            "Origem": origin,
            "Data": self.DT,
            "Id do produto": product_id,
            "Fabricante": manufacturer,
            "Categoria": category,
            "Preço com oferta": price,
            "Preço sem oferta": price_without_discount,
        }
        pprint(json_infos)

        js = json.dumps(json_infos, indent=4, sort_keys=True, ensure_ascii=False)

        output_path = Path("saidas", f"{title}.json")
        output_path.parent.mkdir(exist_ok=True)
        with open(output_path, "w", encoding="utf-8") as f:
            f.write(js)

            # avaliações
            # evaluation = doc.find("div", {"class": "produto-avaliacoes-caroussel"})
            # evaluation = evaluation.find_all("div", {"class": "ratings"})
            # for evaluation in evaluation:
            #     evaluation = evaluation.find_all("p")
            #     f.write(evaluation)


def main():
    scraper = Scraper()
    scraper.navigate_beetween_pages()


if __name__ == "__main__":
    main()
