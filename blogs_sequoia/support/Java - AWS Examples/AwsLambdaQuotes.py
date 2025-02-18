import random

def lambda_handler(event, context):
    
    quotes = [
        'Live free. Die well.',
        'It is a good day to die.',
        'Let darkness burn!',
        'Now is the perfect time to panic!',
        'Knock. Knock.',
        'There is no measure to how fast and how hard I will bring this fight to your door!',
        'Its over.'
    ]

    # print(random.choice(quotes))
    
    return random.choice(quotes)
