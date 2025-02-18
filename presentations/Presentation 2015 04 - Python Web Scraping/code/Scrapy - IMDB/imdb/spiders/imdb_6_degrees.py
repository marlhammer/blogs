# -*- coding: utf-8 -*-
import string

import scrapy

# from py2neo import Graph
# from py2neo import Node

from imdb.items import ImdbPersonPage

class Imdb6DegreesSpider(scrapy.Spider):
    name = "imdb"
    start_urls = (
        'http://www.imdb.com/name/nm0000246',
    )

    people = 0
    peopleLimit = 10

    people_crawled = []

    films_crawled = []

#    graph = Graph("http://54.148.21.172:7474/db/data") # Remote

    def parse(self, response):
        person_id = string.split(response.url, "/")[-2]

#        if (self.has_person_been_crawled(person_id)):
#            return

        personPage = ImdbPersonPage()
        personPage['person'] = response.xpath("//span[@itemprop='name']/text()").extract()[0]
        personPage['person_id'] = person_id
        personPage['films'] = {}
        print('Person: ' + personPage['person_id'])
        for filmElement in response.xpath("//div[@id='filmography']/div[@id='filmo-head-actor']/following-sibling::div[contains(@class, 'filmo-category-section')][1]/div[contains(@class, 'filmo-row')]//a[starts-with(@href, '/title/tt')]"):
            film_id = string.split(filmElement.xpath('@href').extract()[0], '/')[-2]

            personPage['films'][film_id] = filmElement.xpath('text()').extract()[0]

            if (not film_id in self.films_crawled):
                self.films_crawled.append(film_id)
                yield scrapy.Request('http://www.imdb.com' + filmElement.xpath('@href').extract()[0], callback=self.parse_film_page)
        yield personPage
        return

    def parse_film_page(self, response):
        for personElement in response.xpath("//table[contains(@class, 'cast_list')]//td[contains(@itemprop, 'actor')]//a"):
            person_id = string.split(personElement.xpath('@href').extract()[0], "/")[-2]
            if (person_id in self.people_crawled):
                print('Person: ' + person_id + ' ALREADY CRAWLED')
                return

            self.people_crawled.append(person_id)
           
            self.people += 1
            if (self.people <= self.peopleLimit):
                yield scrapy.Request('http://www.imdb.com' + personElement.xpath('@href').extract()[0], callback=self.parse)
        return

#    def has_person_been_crawled(self, person_id):
#        person = list(self.graph.find('Person', property_key='id', property_value=person_id))
#        if (len(person) > 0):
#            print('ALREADY CRAWLED: ' + person_id)
#            return True
#        return False

