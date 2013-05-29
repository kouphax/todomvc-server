<?php

require_once __DIR__.'/../vendor/autoload.php';

use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use TodoMVC\Data\Store;

$app = new Silex\Application();
$app['debug'] = true;

$app['mongo'] = $app->share(function () {
    return new \MongoClient();
});

$app['store'] = $app->share(function ($app) {
    return new Store($app['mongo']);
});

$app->get('/todos', function() use ($app) {
    $store = $app['store'];
    return $app->json($store->getAll());
});

$app->get('/todos/{id}', function($id) use ($app) {
    $store = $app['store'];
    return $app->json($store->get($id));
});

$app->post('/todos', function(Request $request) use ($app) {
    $store = $app['store'];
    $data = json_decode($request->getContent());

    $todo = $store->create($data);
    return $app->json($todo, 201);
});

$app->put('/todos/{id}', function($id, Request $request) use ($app) {
    $store = $app['store'];
    $data = json_decode($request->getContent());

    $store->update($id, $data);
    return new Response('updated', 204);
});

$app->delete('/todos/{id}', function($id) use ($app) {
    $store = $app['store'];
    $store->remove($id);
    return new Response('deleted', 200);
});


return $app;

