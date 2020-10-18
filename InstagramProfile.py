import requests
import re

class InstagramProfile:
    def __init__(self, username, interactive=False):
        self.interactive = interactive
        self.speak("Extracting data page for @{}...".format(username))
        self.src = requests.get("https://instagram.com/{}".format(username)).text
        self.speak("Done!")
        self.speak("Extracting details...")
        self.username = username
        self.name = re.findall(r'full_name":"(.*?)(?<!\\)"', self.src)[0]
        self.bio = re.findall(r'biography":"(.*?)(?<!\\)"', self.src)[0]
        self.verified = True if re.findall(r'is_verified":[a-zA-Z]+', self.src)[0] == "true" else False
        self.id = int(re.findall(r'id":"(\d+?)(?<!\\)"', self.src)[0])
        self.followers = int(re.findall(r'(\d+) Followers', self.src)[0])
        self.following = int(re.findall(r'(\d+) Following', self.src)[0])
        self.pfp = re.findall(r'pic_url_hd":"(.*?)(?<!\\)"', self.src)[0].replace("\\u0026", "&")
        self.number_of_posts = int(re.findall(r'(\d+) Posts', self.src)[0])
        self.speak("Done!")

    def speak(self, message):
        print("BOT SAYS: \"{}\"".format(message)) if self.interactive else None

    def print_details(self):
        print("@{} (Full Name: {})\nInstagram Bio: \"{}\"\nVerified: {}\nInstagram ID: {}\nFollowers: {}\nFollowing: {}".format(self.username, self.name, self.bio, self.verified, self.id, self.followers, self.following))
        print("Profile Picture Link: {}\nNumber of Posts: {}".format(self.pfp, self.number_of_posts))
