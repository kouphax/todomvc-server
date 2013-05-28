<?php
namespace TodoMVC\Model;

class Todo {

    public $id;
    public $title;
    public $order;
    public $completed;

    function __construct($title, $order = 0, $completed = false) {
        $this->title = $title;
        $this->order = $order;
        $this->completed = $completed;
    }

}
