#!/usr/bin/python3

# -*- coding: utf-8 -*-

# TODO navegar entre as páginas dinamicamente

from bs4 import BeautifulSoup
import requests
import os

for i in range(1, 10):
    url = "https://www.superadega.com.br/whisky#" + str(i)
    os.system('python3 navigate_page_2.py {}'.format(url))


#  os.system('python3 navigate_page.py {}'.format(url))


# while not next.parent:
#     url = next['href']
#     result = requests.get(url)
#     doc = BeautifulSoup(result.text, "html.parser")
#     next = doc.find("a", attrs={"title": "Proxima Página"})
#     os.system('python3 navigate_page.py {}'.format(next['href']))
