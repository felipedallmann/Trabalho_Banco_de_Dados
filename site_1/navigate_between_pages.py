#!/usr/bin/python3

# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
import requests
import os


url = "https://www.casadabebida.com.br/whisky/"
result = requests.get(url)
doc = BeautifulSoup(result.text, "html.parser")

next = doc.find("a", attrs={"title": "Proxima Página"})
print(next.parent.has_attr("class"))
os.system('python3 navigate_page.py {}'.format(next['href']))


while not next.parent.has_attr("class"):
    url = next['href']
    result = requests.get(url)
    doc = BeautifulSoup(result.text, "html.parser")
    next = doc.find("a", attrs={"title": "Proxima Página"})
    os.system('python3 navigate_page.py {}'.format(next['href']))
