#!/usr/bin/python3
# -*- encoding: utf-8 -*-

__author__ = "Engine Bai"

import requests

BASE_URL = "https://api.themoviedb.org/3"
IMAGE_BASE_URL = 'http://image.tmdb.org/t/p/'
API_KEY = ""
STAR_WARS_MOVIE_ID = '181812'


def format_api_url(path, query_string=''):
    return '{}{}?api_key={}&{}'.format(BASE_URL, path, API_KEY, query_string)


def request_api(path, query_string=''):
    url = format_api_url(path, query_string)
    print(url)
    r = requests.get(url)
    if r.status_code == requests.codes.ok:
        return r.json()


def home_feed_based_on_movies():
    # Remove latest (that contains only one item)
    categories = ('now_playing', 'popular', 'top_rated', 'upcoming')
    for category in categories:
        r = request_api('/movie/{}'.format(category))
        result_list = r['results']
        for result in result_list:
            print(result)



def home_feed_based_on_genres():
    genre_list = request_api('/genre/movie/list')['genres']
    for genre in genre_list:
        genre_id = genre['id']
        genre_name = genre['name']
        print(genre_name)

        discover_movie_response = request_api('/discover/movie', 'with_genres={}'.format(genre_id))
        for movie in discover_movie_response['results']:
            print('\t', movie['title'])


if __name__ == '__main__':
    # home_feed_based_on_genres()
    home_feed_based_on_movies()
