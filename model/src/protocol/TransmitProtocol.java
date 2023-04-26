package protocol;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The main protocol that will be transmitted over network.
 * Has a general type for a task that dispatchers can dispatch tasks to different handlers.
 * The sub protocols should extend the protocol.
 */
@Data
@NoArgsConstructor
@ToString
public class TransmitProtocol implements Serializable {

    private static final long serialVersionUID = 1234567L;

    protected GeneralType type;

    public TransmitProtocol(GeneralType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransmitProtocol)) return false;
        TransmitProtocol that = (TransmitProtocol) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
