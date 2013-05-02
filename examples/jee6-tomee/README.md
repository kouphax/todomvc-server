*Target platform is TomEE 1.5.2*

Supporting unwrapped JSON
-------------------------

This is quite problematic with the default JSON provider at TomEE. TomEE comes with Apache CXF, which follows some "JSON Standards" and makes it harder to get unwrappeed JSON objects. See the discussion.
* http://stackoverflow.com/questions/10715149/avoid-wrapping-the-object-type-name-from-input-output-json-cxf-web-service
* http://rmannibucau.wordpress.com/2012/10/04/jax-rsjax-ws-configuration-for-tomee-1-5-0/
* http://openejb.979440.n4.nabble.com/Configuring-Apache-CXF-in-TomEE-td4660207.html

Partial support for unwrapped objects is when JSONProvider is additionally configured with dropRootElement

	<Service id="json" class-name="org.apache.cxf.jaxrs.provider.json.JSONProvider">
		dropRootElement = true 
	</Service>
	
This however does not fix wrapping lists. 

Best workaround is to substitute Jettison (the detault) with Jackson: add Maven dependencies and TomEE resource configuration. 
