# Stellar Keyboard — Pardus

Stellar Keyboard'ın Pardus/Linux sürümüdür.

## Teknolojiler

- Python
- IBus
- GTK 3
- PyGObject

## Özellikler

- Türkçe Q klavye
- Türkçe F klavye
- Tam klavye
- Tek el — sol
- Tek el — sağ
- Yüzen klavye
- Emoji paneli
- Emoji kategorileri
- Tema altyapısı
- Otomatik düzeltme
- Kelime önerileri
- Yerel ayarlar

## Varsayılanlar

Klavye ilk açıldığında:

- Düzen: Q
- Mod: Tam klavye
- Tema: Koyu
- Dil: Türkçe

## Yapı

`ibus_engine.py`

IBus motoru.

`stellar_engine.py`

Tuş işleme motoru.

`stellar_ui.py`

GTK klavye arayüzü.

`keyboard_layout.py`

Q/F klavye düzenleri.

`stellar_preferences.py`

Yerel kullanıcı ayarları.

`stellar_emoji.py`

Emoji verileri.

`stellar_modes.py`

Klavye modları.

`stellar_theme.py`

Tema sistemi.

`stellar_autocorrect.py`

Otomatik düzeltme.

`stellar_suggestions.py`

Kelime önerileri.
