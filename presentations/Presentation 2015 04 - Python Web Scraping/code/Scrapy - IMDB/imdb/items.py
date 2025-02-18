# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy

class ImdbPersonPage(scrapy.Item):
    person = scrapy.Field()
    person_id = scrapy.Field()
    films = scrapy.Field()
    pass

