import pygame
from game import Game
from screen import Screen

from factory_sprites import FactorySprites
from bird import Bird
from cloud import Cloud

pygame.init()
screen = pygame.display.set_mode((Screen.width, Screen.height))

level = "difficult"  #select level

factory_flying = FactorySprites()
factory_landscape = FactorySprites()

if level == "easy":
    factory_flying.register(pygame.USEREVENT + 1, Bird(), 1000)
    factory_landscape.register(pygame.USEREVENT + 10, Cloud(), 500)

elif level == "difficult":
    from umbrella import Umbrella
    from mountain import Mountain
    from jet import Jet
    from missile import Missile

    factory_flying.register(pygame.USEREVENT + 1, Bird(), 1000)
    factory_flying.register(pygame.USEREVENT + 2, Umbrella(), 6000)
    factory_flying.register(pygame.USEREVENT + 3, Jet(), 1000)
    factory_flying.register(pygame.USEREVENT + 4, Missile(), 15000)

    factory_landscape.register(pygame.USEREVENT + 10, Cloud(), 500)
    factory_landscape.register(pygame.USEREVENT + 11, Mountain(), 3000)

game = Game(factory_flying, factory_landscape)
game.play()