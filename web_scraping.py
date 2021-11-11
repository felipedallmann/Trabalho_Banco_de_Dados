from typing import Text, final
from bs4 import BeautifulSoup
import sys
import requests


url = sys.argv[1]

result = requests.get(url)
doc = BeautifulSoup(result.text, "html.parser")


# titulo
title = doc.find_all("span", itemprop="name")
title = title[-1].string
print(title)

f = open(title+".txt", "w",encoding='utf-8')


# informações
id = doc.find('input', {'name':'productCode'}).get('value')
manufacturer = doc.find('input', {'name':'productManufacturer'}).get('value')
category = doc.find('input', {'name':'productCategory'}).get('value')
price = doc.find('input', {'name':'productPrice'}).get('value')
price_without_discount = doc.find('input', {'name':'productPricesOf'}).get('value')
f.write("Id do produto: "+ id + "\n")
f.write("Fabricante: " +manufacturer + "\n")
f.write("Categoria: " +category + "\n")
f.write("Preço com oferta: " + price + "\n")
f.write("Preço sem oferta: " + price_without_discount + "\n")

# descrição
description = doc.find( "div", {"class": "produto-descricao"})
text = description.find_all('p')
final_text = ""
for aux_text in text:
    final_text = final_text + aux_text.text
f.write(final_text)

#markers
markers = description.find_all("li")
for marker in markers:
    f.write(marker.text)

# avaliações
# evaluation = doc.find("div", {"class": "produto-avaliacoes-caroussel"})
# evaluation = evaluation.find_all("div", {"class": "ratings"})
# for evaluation in evaluation:
#     evaluation = evaluation.find_all("p")
#     f.write(evaluation)
