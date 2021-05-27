package BlueTree.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
@JsonDeserialize(as = UpdateBody.class)
public class UpdateBody {
	@JsonProperty
	private String id;
	@JsonProperty
	private String field;
	@JsonProperty
	private String value;
}
