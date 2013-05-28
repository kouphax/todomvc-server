<?php
namespace TodoMVC\Model;

class TodoTest extends \PHPUnit_Framework_TestCase {

    public function testShouldCreateObject() {

        $todo = new Todo('title', 1, false);

        $this->assertEquals('title', $todo->title);
        $this->assertEquals(1, $todo->order);
        $this->assertEquals(false, $todo->completed);
    }
}
