# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

from py2neo import Graph
from py2neo import Node
from py2neo import Relationship

class ImdbPersonPagePipeline(object):
    graph = Graph("http://54.148.21.172:7474/db/data") # Remote

    def process_item(self, item, spider):
        print('Putting Person in Neo4J: ' + item['person_id'])
        person_node = self.graph.merge_one("Person", "id", item['person_id'])
        person_node.properties['name'] = item['person']
        person_node.push()
        for film in item['films']:
            film_node = self.graph.merge_one("Film", "id", film)
            film_node.properties['name'] = item['films'][film]
            film_node.push()
            self.graph.create_unique(Relationship(person_node, "ACTED_IN", film_node))
        return item
