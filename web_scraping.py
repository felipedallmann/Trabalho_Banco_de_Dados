from typing import Text, final
from bs4 import BeautifulSoup
import requests

url = "https://www.casadabebida.com.br/whisky/whisky-glenfiddich-12-anos/"

result = requests.get(url)
doc = BeautifulSoup(result.text, "html.parser")


# titulo
title = doc.find_all("span", itemprop="name")
title = title[-1].string
print(title)


# informações
id = doc.find('input', {'name':'productCode'}).get('value')
manufacturer = doc.find('input', {'name':'productManufacturer'}).get('value')
category = doc.find('input', {'name':'productCategory'}).get('value')
price = doc.find('input', {'name':'productPrice'}).get('value')
price_without_discount = doc.find('input', {'name':'productPricesOf'}).get('value')
print("Id do produto: "+ id)
print("Fabricante: " +manufacturer)
print("Categoria: " +category)
print("Preço com oferta: " + price)
print("Preço sem oferta: " + price_without_discount)

# descrição
description = doc.find( "div", {"class": "produto-descricao"})
text = description.find_all('p')
final_text = ""
for aux_text in text:
    final_text = final_text + aux_text.text
print(final_text)

print()
#markers
markers = description.find_all("li")
for marker in markers:
    print(marker.text)

# avaliações
# evaluation = doc.find("div", {"class": "produto-avaliacoes-caroussel"})
# evaluation = evaluation.find_all("div", {"class": "ratings"})
# for evaluation in evaluation:
#     evaluation = evaluation.find_all("p")
#     print(evaluation)
