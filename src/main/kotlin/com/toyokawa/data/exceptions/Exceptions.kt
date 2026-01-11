package com.toyokawa.data.exceptions

sealed class ApplicationException(message: String) : Exception(message)

class ValidationException(message: String) : ApplicationException(message)
class UnauthorizedException(message: String = "Unauthorized") : ApplicationException(message)
class ForbiddenException(message: String = "Forbidden") : ApplicationException(message)
class NotFoundException(message: String = "Not Found") : ApplicationException(message)
class ConflictException(message: String) : ApplicationException(message)
class InternalServerException(message: String = "Internal Server Error") : ApplicationException(message)
