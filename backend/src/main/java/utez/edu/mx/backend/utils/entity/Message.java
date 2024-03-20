package utez.edu.mx.backend.utils.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String text;
    private TypeResponse type;
    private Object result;

    public Message(String text, TypeResponse type) {
        this.text = text;
        this.type = type;
    }

    public Message(Object result, String text, TypeResponse type ) {
        this.text = text;
        this.type = type;
        this.result = result;
    }
}
