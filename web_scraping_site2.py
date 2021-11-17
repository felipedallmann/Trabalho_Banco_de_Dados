#!/usr/bin/python3

# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
import sys
import requests
import json
import os

# url = sys.argv[1]

result = requests.get(
    "https://www.superadega.com.br/whisky-jack-daniels-1l/p")
doc = BeautifulSoup(result.text, "html.parser")

# titulo
title = doc.find("div", {"class": "fn"})
title = title.string
print(title)

# if not os.path.isdir('saidas'):
#     os.mkdir('saidas')


# f = open("saidas/"+title+".json", "w", encoding='utf-8')


# # informações
price_without_discount = doc.find("strong", {"class": "skuListPrice"})
print(price_without_discount.text)
price = doc.find("strong", {"class": "skuBestPrice"})
print(price.text)

origin = doc.find("td", {"class": "value-field Pais"})
if(origin):
    print(origin.text)
else:
    origin = ""

alcohol_content = doc.find("td", {"class": "value-field Teor-alcoolico"})
if(alcohol_content):
    print(alcohol_content.text)
else:
    alcohol_content = ""

ingredients = doc.find("td", {"class": "value-field Ingredientes"})
if(ingredients):
    print(ingredients.text)
else:
    ingredients = ""

age = doc.find("td", {"class": "value-field Envelhecimento"})
if(age):
    print(age.text)
else:
    age = ""

manufacturer = doc.find("td", {"class": "value-field Destilaria"})
if(manufacturer):
    print(manufacturer.text)
else:
    manufacturer = ""

description = doc.find("div", {"class": "productDescription"})
print(description.text)
