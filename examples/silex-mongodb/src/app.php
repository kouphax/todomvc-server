<?php

require_once __DIR__.'/../vendor/autoload.php';

//http://cambridgesoftware.co.uk/blog/item/59-backbonejs-%20-php-with-silex-microframework-%20-mysql

use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use TodoMVC\Model\Todo;

$store = new TodoMVC\Data\Store();

$app = new Silex\Application();
$app['debug'] = true;

$app->get('/todos', function() use ($app, $store) {
    return $app->json($store->getAll());
});

$app->get('/todos/{id}', function($id) use ($app, $store) {
    return $app->json($store->get($id));
});

$app->post('/todos', function(Request $request) use ($app, $store) {
    $data = json_decode($request->getContent());
    $todo = new Todo($data->title, $data->order, $data->completed);
    $store->create($todo);

    return $app->json($todo, 201);
});

$app->put('/todos/{id}', function($id, Request $request) use ($app, $store) {
    $data = json_decode($request->getContent());
    $todo = new Todo($data->title, $data->order, $data->completed);
    $todo->id = $data->id;

    $store->update($id, $todo);
    return new Response('updated', 204);
});

$app->delete('/todos/{id}', function($id) use ($app, $store) {
    $store->remove($id);
    return new Response('deleted', 200);
});


return $app;

