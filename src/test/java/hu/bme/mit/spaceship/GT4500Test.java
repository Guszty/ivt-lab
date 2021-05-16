package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockTS1;
  private TorpedoStore mockTS2;

  @BeforeEach
  public void init(){
    mockTS1 = mock(TorpedoStore.class);
    mockTS2 = mock(TorpedoStore.class);
    this.ship = new GT4500(mockTS1, mockTS2);
  }

  @Test
  public void fireTorpedo_SINGLE_Success(){
    // Arrange
    when (mockTS1.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_SINGLE_Failure(){
    // Arrange
    when (mockTS1.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void firePrimaryTorpedo_SINGLE_Success(){
    // Arrange
    when(mockTS1.fire(1)).thenReturn(true);
    when(mockTS2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1,times(1)).fire(1);
    verify(mockTS2,times(0)).fire(1);
    assertEquals(true, result);
  }
  
  @Test
  public void fireSecondaryTorpedo_SINGLE_Success(){

    // Arrange
    when(mockTS1.fire(1)).thenReturn(false);
    when(mockTS2.fire(1)).thenReturn(true);

    // Act
    //torpedo váltása
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1,times(1)).fire(1);
    verify(mockTS2,times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void firePrimaryTorpedo_SINGLE_PrimaryEmptySecondaryHasTorpedo_Success(){
    // Arrange
    when(mockTS1.fire(1)).thenReturn(false);
    when(mockTS2.fire(1)).thenReturn(true);
    when(mockTS1.isEmpty()).thenReturn(true);
    when(mockTS2.isEmpty()).thenReturn(false);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertEquals(true, result);
  }

  @Test
  public void fireSecondaryTorpedo_SINGLE_SecondaryEmptyPrimaryHasTorpedo_Success(){
    // Arrange
    when(mockTS1.fire(1)).thenReturn(true);
    when(mockTS2.fire(1)).thenReturn(false);
    when(mockTS1.isEmpty()).thenReturn(false);
    when(mockTS2.isEmpty()).thenReturn(true);

    //Act
    //primary lő -> secondary legyen
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_ALL_Success(){
    // Arrange
    when (mockTS1.fire(1)).thenReturn(true);
    when (mockTS2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTS1, times(1)).fire(1);
    verify(mockTS2, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_ALL_Failure(){
    // Arrange
    when (mockTS1.fire(1)).thenReturn(false);
    when (mockTS2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTS1, times(1)).fire(1);
    verify(mockTS2, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_ALL_WithOneFilled_Failure(){
    // Arrange
    when(mockTS1.fire(1)).thenReturn(true);
    when(mockTS2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTS1,times(1)).fire(1);
    verify(mockTS2,times(1)).fire(1);
    assertEquals(false, result);
  }
  
  @Test
  public void fireTorpedo_ALL_PrimaryTorpedoStoreEmpty_Failure(){
    //Arrange
    when(mockTS1.isEmpty()).thenReturn(true);
    when(mockTS2.isEmpty()).thenReturn(false);
    when(mockTS1.fire(1)).thenReturn(false);
    when(mockTS2.fire(1)).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    assertEquals(false, result);
  }

}
