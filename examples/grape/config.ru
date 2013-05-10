require "rubygems"
require "bundler/setup"
require 'grape'
require './store'
require './todos_api'

use Rack::Session::Cookie
app = Rack::Builder.new do
  use Rack::Static, :urls => ["/js", "/components"], :root => "public"
  map "/" do
    run Rack::File.new("public/index.htm")
  end
end.to_app
run Rack::Cascade.new [app, TodosAPI]
