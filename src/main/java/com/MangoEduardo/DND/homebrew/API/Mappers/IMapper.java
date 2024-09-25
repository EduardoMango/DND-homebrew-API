package com.MangoEduardo.DND.homebrew.API.Mappers;

public interface IMapper <A,B> {

        B mapTo(A a);

        A mapFrom(B b);
}
