import httplib2
import json

# Parameters:
#
character_name = "Marlhammer"

server = "Khadgar"

# Script:
#
base_url="http://us.battle.net/api/wow/character/"
base_url+=server
base_url+="/"
base_url+=character_name
base_url+="?fields=achievements,appearance,mounts,pets,professions,progression,pvp,quests,reputation,stats,talents,titles"

resp, content = httplib2.Http().request(base_url)

output = open(character_name + '.json', 'w')

character_json = json.loads(content)

json.dump(character_json, output, sort_keys=True, indent=4, separators=(',', ': '))
output.write("\n")

for achievement in character_json["achievements"]["achievementsCompleted"]:
    resp, content = httplib2.Http().request("http://us.battle.net/api/wow/achievement/"+str(achievement))
    achievement_json = json.loads(content)
    json.dump(achievement_json, output, sort_keys=True, indent=4, separators=(',', ': '))
    output.write("\n")
    print str(achievement)

print "\nCompleted!\n"


