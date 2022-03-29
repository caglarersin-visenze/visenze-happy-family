package com.visenze.happyfamily.model.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.visenze.happyfamily.model.ProductWanted;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
	
	@NotNull(message = "wantedProducts cannot be null")
	@NotEmpty(message = "wantedProducts cannot be empty")
	@Valid
	@Singular
	private List<ProductWanted> wantedProducts;

}
