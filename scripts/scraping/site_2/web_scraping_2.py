#!/usr/bin/python3

import json
from datetime import date
from pprint import pprint
from pathlib import Path

import requests
from bs4 import BeautifulSoup


class Scraper:
    DT = date.today().strftime("%d/%m/%Y")
    ROOT_URL = "https://www.superadega.com.br"

    def navigate_beetween_pages(self):
        for i in range(1, 10):
            url = f"{self.ROOT_URL}/whisky#{i}"
            print(url)
            self.navigate_page(url)

    def navigate_page(self, url):
        result = requests.get(url)
        doc = BeautifulSoup(result.text, "html.parser")

        products_page = doc.find(class_="collection n3colunas")
        products = products_page.find_all("li", {"layout": "3c97f6ef-8345-4fb9-9db6-76b5fb256fd1"})

        for link in products:
            link = link.find("a")
            print(link["href"])
            self.scrape(link["href"])
            print()

    def scrape(self, url):
        result = requests.get(url)
        doc = BeautifulSoup(result.text, "html.parser")

        # titulo
        title = doc.find("div", {"class": "fn"})
        title = title.text.replace(r"\𝟯𝟰𝟮\𝟮𝟬𝟬\𝟮𝟯𝟭𝘀", "").replace("/", "")
        print(title)

        # informações
        price_without_discount = doc.find("strong", {"class": "skuListPrice"})
        if price_without_discount:
            price_without_discount = price_without_discount.text.strip()
        else:
            price_without_discount = ""

        price = doc.find("strong", {"class": "skuBestPrice"})
        if price:
            price = price.text.strip()
        else:
            price = ""

        # Limpa os preços
        replaces = [("R$", ""), (".", ""), (",", ".")]
        for (old, new) in replaces:
            price_without_discount = price_without_discount.replace(old, new).strip()
            price = price.replace(old, new).strip()

        origin = doc.find("td", {"class": "value-field Pais"})
        if origin:
            origin = origin.text.strip()
        else:
            origin = ""

        alcohol_content = doc.find("td", {"class": "value-field Teor-alcoolico"})
        if alcohol_content:
            alcohol_content = alcohol_content.text.strip()
        else:
            alcohol_content = ""

        ingredients = doc.find("td", {"class": "value-field Ingredientes"})
        if ingredients:
            ingredients = ingredients.text.strip()
        else:
            ingredients = ""

        age = doc.find("td", {"class": "value-field Envelhecimento"})
        if age:
            age = age.text.strip()
        else:
            age = ""

        manufacturer = doc.find("td", {"class": "value-field Destilaria"})
        if manufacturer:
            manufacturer = manufacturer.text.strip()
        else:
            manufacturer = ""

        description = doc.find("div", {"class": "productDescription"}).text.strip()

        json_infos = {
            "Nome": title,
            "Descrição": description,
            "Data": self.DT,
            "Fabricante": manufacturer,
            "Preço com oferta": price,
            "Preço sem oferta": price_without_discount,
            "Idade": age,
            "Ingredientes": ingredients,
            "Teor alcoólico": alcohol_content,
            "Origem": origin,
        }
        pprint(json_infos)

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
