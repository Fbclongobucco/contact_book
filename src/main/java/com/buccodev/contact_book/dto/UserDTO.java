package com.buccodev.contact_book.dto;

import java.util.List;

public record UserDTO(String name, String password, String email, List<ContactDTO> contacts) {
}
