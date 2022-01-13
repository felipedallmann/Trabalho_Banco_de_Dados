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
title = doc.find("div", {"class": "fn"})
title = title.text
print(title)

if not os.path.isdir('saidas'):
    os.mkdir('saidas')


f = open("saidas/"+title+".json", "w", encoding='utf-8')


# # informações
price_without_discount = doc.find("strong", {"class": "skuListPrice"})
if(price_without_discount):
    price_without_discount = price_without_discount.text
else:
    price_without_discount = "Produto indisponível"

price = doc.find("strong", {"class": "skuBestPrice"})
if(price):
    price = price.text
else:
    price = "Produto indisponível"

origin = doc.find("td", {"class": "value-field Pais"})
if(origin):
    origin = origin.text
else:
    origin = ""

alcohol_content = doc.find("td", {"class": "value-field Teor-alcoolico"})
if(alcohol_content):
    alcohol_content = alcohol_content.text
else:
    alcohol_content = ""

ingredients = doc.find("td", {"class": "value-field Ingredientes"})
if(ingredients):
    ingredients = ingredients.text
else:
    ingredients = ""

age = doc.find("td", {"class": "value-field Envelhecimento"})
if(age):
    age = age.text
else:
    age = ""

manufacturer = doc.find("td", {"class": "value-field Destilaria"})
if(manufacturer):
    manufacturer = manufacturer.text
else:
    manufacturer = ""

info = {
    "Fabricante": manufacturer,
    "Preço com oferta": price,
    "Preço sem oferta":  price_without_discount,
    "Idade": age,
    "Ingredientes": ingredients,
    "Teor alcoólico": alcohol_content,
    "Origem": origin
}

description = doc.find("div", {"class": "productDescription"})

descr = {
    "Descrição": description.text
}

json_infos = {
    "Informações": info,
    "Descrição": descr,
    "Data": dt
}

js = json.dumps(json_infos, indent=4, sort_keys=True, ensure_ascii=False)
f.write(js)
