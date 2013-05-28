<?php
namespace TodoMVC\Data;

use TodoMVC\Model\Todo;

class Store {

    private $collection;

    public function __construct() {
        $m = new \MongoClient();
        $this->collection = $m->store->todos;
    }

    public function get($id) {
        return $todo = $this->collection->findOne(array('id' => $id), array('_id' => 0));
    }

    public function getAll() {
        $a = array();
        $c = $this->collection->find(array(), array('_id' => 0));
        foreach ($c as $todo) {
            array_push($a, $todo);
        }

        return $a;
    }

    public function create($todo) {
        $todo->_id = new \MongoId();
        $todo->id = (string) $todo->_id;
        return $this->collection->insert($todo);
    }

    public function update($id, $todo) {
        return $this->collection->update(array('id' => $id), $todo);
    }

    public function remove($id) {
        $this->collection->remove(array('id' => $id));
    }

}