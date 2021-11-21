#!/usr/bin/python3

# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
import requests
import os


headers = {
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:55.0) Gecko/20100101 Firefox/55.0',
}

url = "https://caledoniastore.com.br/"
result = requests.get(url, headers=headers)
doc = BeautifulSoup(result.text, "html.parser")

paginas = doc.find_all(class_="category_grid_box")

for pagina in paginas:
    pagina = pagina.find("a")
    if(pagina['href'] != "https://caledoniastore.com.br/categoria/coquetelaria/"):
        os.system('python3 navigate_page_3.py {}'.format(pagina['href']))
