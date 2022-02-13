#!/usr/bin/python3

# -*- coding: utf-8 -*-
from datetime import date
from bs4 import BeautifulSoup
import sys
import requests
import json
import os

today = date.today()
dt = today.strftime("%d/%m/%Y")

url = sys.argv[1]

result = requests.get(url)
doc = BeautifulSoup(result.text, "html.parser")

# titulo
title = doc.find_all("span", itemprop="name")
title = title[-1].string
print(title)

if not os.path.isdir("saidas"):
    os.mkdir("saidas")


f = open("saidas/" + title + ".json", "w", encoding="utf-8")


# informações
id = doc.find("input", {"name": "productCode"}).get("value").strip()
manufacturer = doc.find("input", {"name": "productManufacturer"}).get("value").strip()
category = doc.find("input", {"name": "productCategory"}).get("value").strip()
price = doc.find("input", {"name": "productPrice"}).get("value").strip()
price_without_discount = doc.find("input", {"name": "productPricesOf"}).get("value").strip()

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

list_of_markers = []
for marker in markers:
    marker = marker.text
    marker = "".join(marker.split("\t"))
    marker = "".join(marker.split("\n")).strip()
    if marker != "":
        print(marker)
        list_of_markers.append(marker)

print(list_of_markers)
json_infos = {
    "Descrição": final_text,
    "Marcadores": list_of_markers,
    "Data": dt,
    "Id do produto": id,
    "Fabricante": manufacturer,
    "Categoria": category,
    "Preço com oferta": price,
    "Preço sem oferta": price_without_discount,
}

js = json.dumps(json_infos, indent=4, sort_keys=True, ensure_ascii=False)
f.write(js)


# avaliações
# evaluation = doc.find("div", {"class": "produto-avaliacoes-caroussel"})
# evaluation = evaluation.find_all("div", {"class": "ratings"})
# for evaluation in evaluation:
#     evaluation = evaluation.find_all("p")
#     f.write(evaluation)
