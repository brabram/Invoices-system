package pl.coderstrust.helloWorld;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class FooBarTest {
    @Test
    public void testForPositiveNumberAsArgument() {
        //given
        int size = 5;
        List<String> expected = Arrays.asList("0 FooBar", "1 ", "2 ", "3 Foo", "4 ", "5 Bar");

        //when
        List<String> actual = FooBar.getFooBar(size);

        //then
        Assert.assertThat(actual, is(expected));
    }
}
