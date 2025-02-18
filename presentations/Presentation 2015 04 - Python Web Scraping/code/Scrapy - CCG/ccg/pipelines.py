# -*- coding: utf-8 -*-

import string

import scrapy

from scrapy.contrib.pipeline.images import ImagesPipeline

class CardImagePipeline(ImagesPipeline):
    def file_path(self, request, response=None, info=None):
        return string.split(request.url, '/')[-3] + '/' + string.split(request.url, '/')[-1]

