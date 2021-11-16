from typing import Text, final
from bs4 import BeautifulSoup
import requests
import os
import sys

url = sys.argv[1]

result = requests.get(url)
doc = BeautifulSoup(result.text, "html.parser")

products_page = doc.find(class_="row m-0 px-0")
products = products_page.find_all(class_="col-6 col-md-6 col-lg-4 px-0 px-md-2 mb-5 produtos-categoria-thumb")

link = products[0]
link = link.find("a")
print(link['href'])


for link in products:
    link = link.find("a")
    print(link['href'])
    os.system('python web_scraping.py {}'.format(link['href']))
    print()
