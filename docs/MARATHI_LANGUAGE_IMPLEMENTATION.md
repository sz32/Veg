# Marathi Language Implementation

## Overview
Marathi language support has been successfully added to the AI Agent application.

## Changes Made

### 1. Created Marathi String Resources
- **File**: `/app/src/main/res/values-mr/strings.xml`
- Added complete Marathi translations for all app strings including:
  - Product list screen
  - Checkout screen
  - Settings screen
  - All UI elements

### 2. Updated Language Selection
- **File**: `/settings/src/main/java/com/ai/settings/ui/SettingsScreen.kt`
- Added Marathi ("mr") to the language selection dialog
- Updated `getLanguageName()` function to handle Marathi language code
- Marathi now appears in the language list alongside English, Gujarati, Hindi, and Odia

### 3. Updated String Resources
Added "Marathi" string resource in all language files:
- `/app/src/main/res/values/strings.xml` - "Marathi"
- `/app/src/main/res/values-hi/strings.xml` - "मराठी"
- `/app/src/main/res/values-gu/strings.xml` - "મરાઠી"
- `/app/src/main/res/values-or/strings.xml` - "ମରାଠୀ"
- `/app/src/main/res/values-mr/strings.xml` - "मराठी"
- `/settings/src/main/res/values/strings.xml` - "Marathi"

## Language Code
- **ISO 639-1 Code**: `mr` (Marathi)

## How to Use
1. Open the app
2. Navigate to Settings
3. Tap on "Language"
4. Select "Marathi" (मराठी) from the list
5. The app will restart and display all content in Marathi

## Supported Languages (After Implementation)
1. English (en)
2. Gujarati (gu)
3. Hindi (hi)
4. Marathi (mr) ✨ **NEW**
5. Odia (or)

## Testing
- ✅ Build successful
- ✅ All resources properly localized
- ✅ Language selection UI updated
- ✅ Activity restart mechanism works for language changes

## Technical Details
- The language preference is stored using DataStore
- Language changes trigger an activity restart to apply the new locale
- The Marathi locale (mr) is automatically applied when selected
- All UI strings are properly translated and formatted

