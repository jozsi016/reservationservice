package hu.jnagy.reservationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hu.jnagy.reservationservice.model.Reservation;
import hu.jnagy.reservationservice.api.ReservationResponse;
import hu.jnagy.reservationservice.service.ReservationService;
import hu.jnagy.reservationservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservationService reservationServiceMock;
    @MockBean
    private UserService userService;

    @Test
    public void shouldCreateReservation() throws Exception {
        //given
        Reservation expected = new Reservation(
                1, 1, 1, LocalDate.of(2021, 2, 15), LocalDate.of(2021, 2, 20), 25000);
        when(reservationServiceMock.getReservationById(anyLong())).thenReturn(expected);
        //when
        String actual = this.mockMvc.perform(get("/reservation/1")).andDo(print()).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        //then
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        ReservationResponse actualReservation = om.readValue(actual, ReservationResponse.class);
        assertThat(actualReservation.getReservation(), is(expected));
        verify(reservationServiceMock).getReservationById(1L);
    }
}
