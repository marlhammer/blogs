# -*- coding: utf-8 -*-

# Scrapy settings for ccg project
#
# For simplicity, this file contains only the most important settings by
# default. All the other settings are documented here:
#
#     http://doc.scrapy.org/en/latest/topics/settings.html
#

BOT_NAME = 'ccg'

SPIDER_MODULES = ['ccg.spiders']
NEWSPIDER_MODULE = 'ccg.spiders'

# Crawl responsibly by identifying yourself (and your website) on the user-agent
#USER_AGENT = 'ccg (+http://www.yourdomain.com)'

ITEM_PIPELINES = {'ccg.pipelines.CardImagePipeline': 1}

IMAGES_STORE = '/home/pi/work/scrapy/images'

