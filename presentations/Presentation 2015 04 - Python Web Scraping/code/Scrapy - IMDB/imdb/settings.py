# -*- coding: utf-8 -*-

# Scrapy settings for imdb project
#
# For simplicity, this file contains only the most important settings by
# default. All the other settings are documented here:
#
#     http://doc.scrapy.org/en/latest/topics/settings.html
#

BOT_NAME = 'imdb-6-degrees'

SPIDER_MODULES = ['imdb.spiders']
NEWSPIDER_MODULE = 'imdb.spiders'

# Crawl responsibly by identifying yourself (and your website) on the user-agent
#USER_AGENT = 'imdb (+http://www.yourdomain.com)'

ITEM_PIPELINES = {'imdb.pipelines.ImdbPersonPagePipeline': 1}

LOG_LEVEL = 'WARNING'

