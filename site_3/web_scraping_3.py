#!/usr/bin/python3

# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
import sys
import requests
import json
import os

from requests.api import head

url = sys.argv[1]


# Python code to convert into dictionary
def Convert(tup, di):
    di = dict(tup)
    return di


headers = {
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:55.0) Gecko/20100101 Firefox/55.0',
}

result = requests.get(
    url, headers=headers)
doc = BeautifulSoup(result.text, "html.parser")

# titulo
title = doc.find("div", {"class": "product_summary_middle"})
title = title.text
title = "".join(title.split('\n'))
title = "".join(title.rstrip())
print(title)

if not os.path.isdir('saidas'):
    os.mkdir('saidas')


f = open("saidas/"+title+".json", "w", encoding='utf-8')


# # informações
price = doc.find("span", {"class": "woocommerce-Price-amount amount"})
if price:
    price = price.text
else:
    price = ""

markers = doc.find(
    "div", {"class": "woocommerce-product-details__short-description"})

if markers:
    markers = markers.get_text(strip=True, separator='\n').splitlines()
    markers.pop(0)
    for i in range(0, len(markers)):
        markers[i] = markers[i].split(':')

    markers.insert(0, ['Preço', price])
    # converting into dict
    markers = tuple(markers)
    # print(tuple(markers))
    dictionary = {}
    markers = Convert(markers, dictionary)
    print(markers)
else:
    markers = ""
    info = {
        "Informações": "Informações indisponiveis"
    }

description = doc.find(
    "div", {"id": "tab-description"})
if description:
    description = description.p.text
else:
    description = ""

descript = {
    "Descrição": description
}

json_infos = {
    "Título": title,
    "Informações": markers,
    "Descrição": descript
}

js = json.dumps(json_infos, indent=4, sort_keys=True, ensure_ascii=False)
f.write(js)
