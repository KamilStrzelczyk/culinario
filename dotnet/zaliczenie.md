# Culinario – wymogi zaliczeniowe

Zaimplementowano rozwiązania wymagane do zaliczenia laboratorium z Programowania Aplikacji w ASP.NET:

- AJAX i responsywna obsługa danych po stronie klienta – strona główna pobiera listę przepisów przez `fetch()` i odświeża widok bez przeładowania strony.
- WebSocket – dodany endpoint `live/ws`, który wysyła informacje o aktywności serwera do klienta w czasie rzeczywistym.
- Lock/Mutex – użyty jest `Mutex` do ochrony stanu aktywności aplikacji.
- Semaphore – użyty jest `SemaphoreSlim` do synchronizacji dostępu do stanu aktywności.
- ThreadPool – użyto `ThreadPool.QueueUserWorkItem` do uruchamiania operacji po stronie serwera bez blokowania żądań użytkownika.

Dodatkowo utrzymano dotychczasową architekturę .NET bez ingerencji w projekt Kotlin.

Najważniejsze zmiany:

- rozszerzenie usługi przepisów o synchronizację wątków i aktualizację stanu aktywności,
- dodanie endpointu live dla WebSocket i statusu AJAX,
- poprawa widoku głównego o responsywny panel live oraz listę danych pobieranych asynchronicznie,
- dodanie pliku dokumentacyjnego opisującego realizację wymagań.
