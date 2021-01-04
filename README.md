# Edge Lords

Edge Lords is a fan recreation of the mini-game _Vantage Masters_ from  _Trails of Cold Steel 3_ and _4_.

## Background
Vantage Masters is a card game played between two people. The easiest comparison that can be made is to games like _Magic: The Gathering_ or _Hearthstone_. It has its own twists on the format but if you're familiar with those games you're off to a good start.

I've drafted a [Google Doc](https://docs.google.com/document/d/13izzV3TjRIEXKKgi8foEPB1zTqEirpIgQqRjUJtnrLI/edit?usp=sharing) explaining the rules as best as I know them. If you're not familiar with _Vantage Masters_, I'd recommend reading that. I've also been referencing this [GameFAQs guide (spoiler warning!)](https://gamefaqs.gamespot.com/ps4/229684-the-legend-of-heroes-trails-of-cold-steel-iv/faqs/77559/vantage-masters) for the card list and other miscellaneous information.

## Goals
The main goal of this project right now is to create a backend that can be utilized by any number of clients. The backend will handle deck assembly and game state. Once I'm at a comfortable place with that, I will begin making some kind of client.

Backend milestones:

* Game state can be serialized and saved somewhere
* Natials and Masters can move around the board and attack each other (in a bootstrapped game)
* Players can summon new Natials (in a bootstrapped game)
* A game can be started from scratch and players can take turns
* Victory condition is checked and acted upon
* Natial and Master skills can be used
* Natial and Master abilities are triggered
* Spells can be used

## Discord
This project is being completed as part of the Cincinnati Technology and Software Engineering Discord's hackathon. Message me `@SirBraneDamuj#0001` on Discord if you want to know more about the Discord.
