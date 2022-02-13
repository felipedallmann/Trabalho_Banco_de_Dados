#!/usr/bin/python3

# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
import requests
import os
import sys


result = requests.get(sys.argv[1])
doc = BeautifulSoup(result.text, "html.parser")

products_page = doc.find(class_="collection n3colunas")
products = products_page.find_all(
    "li", {"layout": "ce56140d-7c05-4428-a765-faaedbac10c8"})

# link = products[3]
# link = link.find("a")
# print(link['href'])


for link in products:
    link = link.find("a")
    print(link['href'])
    os.system('python3 web_scraping_2.py {}'.format(link['href']))
    print()
