package protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

// set type of the operation
@Data
@ToString
@NoArgsConstructor
public class TransmitProtocol {
    private static final long serialVersionUID = 1234567L;

    protected GeneralType generalType;

    public TransmitProtocol(GeneralType generalType) {
        this.generalType = generalType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransmitProtocol)) return false;
        TransmitProtocol that = (TransmitProtocol) o;
        return generalType == that.generalType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(generalType);
    }
}
