package com.example.springcourse.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageRequestModel {
	
	private int page = 0;
	private int size = 10;
	private String sort = "";
	
	public PageRequestModel(Map<String, String> params) {
		if(params.containsKey("page")) page = Integer.parseInt(params.get("page"));
		if(params.containsKey("size")) size = Integer.parseInt(params.get("size"));
		if(params.containsKey("sort")) sort = params.get("sort");
	}
	
	public PageRequest toSpringPageRequest() {
		List<Order> orders = new ArrayList<>();
		String[] props = sort.split(",");
		
		for (String prop : props) {
			prop = prop.trim();
			if(prop.isEmpty())
				continue;
			
			if(prop.startsWith("-")) {
				prop = prop.replace("-", "");
				orders.add(Order.desc(prop));
			} else {
				orders.add(Order.asc(prop));
			}
		}
		
		return PageRequest.of(page, size, Sort.by(orders));
	}

}
