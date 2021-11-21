#!/usr/bin/python3

# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
import requests
import os
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
import sys
import time
from selenium.webdriver.chrome.webdriver import WebDriver
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.common.exceptions import TimeoutException


url = sys.argv[1]
option = Options()

# faz com que o navegador não abra, faz tudo em background
option.headless = True
driver = webdriver.Chrome("drivers/chromedriver", options=option)
driver.get(url)


try:
    element = driver.find_element_by_class_name(
        "getbowtied_ajax_load_button")
    element = element.find_element_by_css_selector('a')
    element.click()
    # WebDriverWait(driver, 10).until(
    #     EC.NoSuchElementException((By.CLASS_NAME, 'getbowtied_ajax_load_button')))
    time.sleep(5)
except:
    print("Loading took too much time!")

result = driver.page_source
doc = BeautifulSoup(result, "html.parser")
products_page = doc.find(class_="products columns-6")
products_page = products_page.find_all("li")

for link in products_page:
    link = link.find("a")
    os.system('python3 web_scraping_3.py {}'.format(link['href']))
    print(link['href'])

driver.quit
