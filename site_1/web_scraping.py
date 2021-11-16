from typing import Text, final
from bs4 import BeautifulSoup
import sys
import requests
import json
import os

url = sys.argv[1]

result = requests.get(url)
doc = BeautifulSoup(result.text, "html.parser")


# titulo
title = doc.find_all("span", itemprop="name")
title = title[-1].string
print(title)

if not os.path.isdir('saidas'):
    os.mkdir('saidas')
	

f = open("saidas/"+title+".json", "w",encoding='utf-8')


# informações
id = doc.find('input', {'name':'productCode'}).get('value')
manufacturer = doc.find('input', {'name':'productManufacturer'}).get('value')
category = doc.find('input', {'name':'productCategory'}).get('value')
price = doc.find('input', {'name':'productPrice'}).get('value')
price_without_discount = doc.find('input', {'name':'productPricesOf'}).get('value')
info = {
    "Id do produto" : id,
    "Fabricante" : manufacturer, 
    "Categoria" : category,
    "Preço com oferta" : price,
    "Preço sem oferta" :  price_without_discount
}
# descrição
description = doc.find( "div", {"class": "produto-descricao"})
text = description.find_all('p')
final_text = ""
for aux_text in text:
    final_text = final_text + aux_text.text

final_text = ''.join(final_text.split('\t'))
final_text = ''.join(final_text.split('\n'))
descr = {
    "Descrição" : final_text
}

#markers
markers = description.find_all("li")

list_of_markers = []
for marker in markers:
    marker = marker.text
    marker = ''.join(marker.split('\t'))
    marker = ''.join(marker.split('\n'))
    print(marker)
    list_of_markers.append(marker)

marker_dict = {
    "Marcadores" : list_of_markers
}

print(marker_dict)
json_infos = {
    "Informações" : info,
    "Descrição" : descr,
    "Marcadores" : marker_dict
}

js = json.dumps(json_infos, indent=4, sort_keys= True, ensure_ascii=False )
f.write(js)


# avaliações
# evaluation = doc.find("div", {"class": "produto-avaliacoes-caroussel"})
# evaluation = evaluation.find_all("div", {"class": "ratings"})
# for evaluation in evaluation:
#     evaluation = evaluation.find_all("p")
#     f.write(evaluation)
