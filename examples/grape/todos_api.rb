class TodosAPI < Grape::API
  format :json

  resource 'todos' do
    get "/" do
      Store.get_all
    end

    get "/:id" do
      Store.get(params.id) || error!("Not Found", 404)
    end

    post "/" do
      Store.save params
    end

    put "/:id" do
      error!("Not Found", 404) unless Store.get params.id
      Store.update(params.id, params)
    end

    delete "/:id" do
      Store.delete params.id
    end
  end
end
