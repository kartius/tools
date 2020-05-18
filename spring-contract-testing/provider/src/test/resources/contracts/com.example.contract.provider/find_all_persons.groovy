import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should return person by id=1"

	request {
		url "/persons"
		method GET()
	}

	response {
		status OK()
		headers {
			contentType applicationJson()
		}
		body(
			"persons":[
                   [
					"id": 3,
					"name": "foo",
					"surname": "bee"
					],
                    [
					id     : 2,
					name   : "foo2",
					surname: "bee2"
		            ]
			]

		)
	}
}