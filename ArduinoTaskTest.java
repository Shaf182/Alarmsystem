import org.firmata4j.IODevice;

import org.firmata4j.Pin;

import java.io.IOException;

class ArduinoTaskTest {

    private ArduinoTask arduinoTask;
    private IODevice mockIODevice;

    @BeforeEach
    void setUp() throws IOException {
        mockIODevice = new MockIODevice();
        arduinoTask = new ArduinoTask(0, mockIODevice);
    }

    @Test
    void testGetPotValue() {
        // Given
        long expectedValue = 100;

        // When
        ((MockIODevice) mockIODevice).setValue(14, expectedValue);
        long actualValue = arduinoTask.getPotValue();

        // Then
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void testRunLedOn() throws IOException {
        // Given
        Pin mockLed = mock(Pin.class);
        arduinoTask = new ArduinoTask(1, mockIODevice);
        when(mockIODevice.getPin(4)).thenReturn(mockLed);

        // When
        arduinoTask.run();

        // Then
        verify(mockLed).setValue(1);
    }

    @Test
    void testRunLedOff() throws IOException {
        // Given
        Pin mockLed = mock(Pin.class);
        arduinoTask = new ArduinoTask(0, mockIODevice);
        when(mockIODevice.getPin(4)).thenReturn(mockLed);

        // When
        arduinoTask.run();

        // Then
        verify(mockLed).setValue(1);
    }

    @Test
    void testRunInvalidPinMode() throws IOException {
        // Given
        Pin mockLed = mock(Pin.class);
        arduinoTask = new ArduinoTask(1, mockIODevice);
        when(mockIODevice.getPin(4)).thenReturn(mockLed);
        doThrow(IOException.class).when(mockLed).setMode(Pin.Mode.OUTPUT);

        // When
        arduinoTask.run();

        // Then
        // Check if IOException is thrown
        assertThrows(IOException.class, () -> arduinoTask.run());
    }
}