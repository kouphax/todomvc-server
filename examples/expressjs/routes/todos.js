
/*
 * GET users listing.
 */

module.exports = function(app, store) {

  app.get('/todos', function(req, res){
    res.json(store.getAll());
  });

  app.post('/todos', function(req, res){
    var todo = store.save(req.body);
    res.json(todo);
  });

  app.put('/todos/:id', function(req, res){
    var id = parseInt(req.params['id'], 10)
    var todo = store.update(id, req.body);
    res.json(todo);
  })

  app.delete('/todos/:id', function(req, res){
    var id = parseInt(req.params['id'], 10)
    store.delete(id)
    res.send("")
  })
}