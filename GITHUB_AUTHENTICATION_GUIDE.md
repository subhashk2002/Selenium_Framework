# GitHub Authentication Guide

## ⚠️ Issue
GitHub no longer accepts password authentication. You need either:
1. **Personal Access Token (PAT)** - Simpler, recommended for beginners
2. **SSH Keys** - More secure, recommended for ongoing work

---

## 🔑 Option 1: Personal Access Token (RECOMMENDED)

### Step 1: Generate Personal Access Token

1. Go to: https://github.com/settings/tokens
2. Click "Generate new token"
3. Fill in:
   - **Note:** `Selenium Framework`
   - **Expiration:** 90 days (or longer)
   - **Select scopes:** Check `repo` (Full control of private repositories)
4. Click "Generate token"
5. **Copy the token** (you won't see it again!)

### Step 2: Use Token to Push

Use the token as your password when pushing. When prompted for password, paste the token:

```bash
cd C:\Users\Hp\Downloads\Selenium_Java
git push -u origin main
```

When prompted:
- Username: `subhashk2002`
- Password: Paste your Personal Access Token

### Step 3: Store Token (Windows)

To avoid entering token every time, store it in Windows Credential Manager:

**PowerShell:**
```powershell
cmdkey /add:github.com /user:subhashk2002 /pass:YOUR_PAT_TOKEN
```

Replace `YOUR_PAT_TOKEN` with your actual token.

---

## 🔐 Option 2: SSH Keys (More Secure)

### Step 1: Generate SSH Key

**PowerShell or Git Bash:**
```bash
ssh-keygen -t ed25519 -C "emmacarlos919@gmail.com"
```

When prompted:
- File location: Press Enter (use default)
- Passphrase: Leave empty (press Enter)
- Confirm: Leave empty (press Enter)

### Step 2: Add SSH Key to GitHub

1. Copy your SSH public key:
```bash
# Windows (PowerShell)
Get-Content $env:USERPROFILE\.ssh\id_ed25519.pub | Set-Clipboard

# Or manual:
# Open: C:\Users\Hp\.ssh\id_ed25519.pub with Notepad
# Copy the content
```

2. Go to: https://github.com/settings/keys
3. Click "New SSH key"
4. Fill in:
   - **Title:** `My Development Machine`
   - **Key:** Paste your public key
5. Click "Add SSH key"

### Step 3: Update Remote URL

Change from HTTPS to SSH:

```bash
cd C:\Users\Hp\Downloads\Selenium_Java
git remote remove origin
git remote add origin git@github.com:subhashk2002/Selenium_Framework.git
git push -u origin main
```

### Step 4: Verify SSH Connection

```bash
ssh -T git@github.com
```

Should output: `Hi subhashk2002! You've successfully authenticated...`

---

## ✅ Recommended Solution

For your situation, **Option 1 (Personal Access Token)** is simpler:

### Quick Steps:

1. **Generate Token:**
   - Go to: https://github.com/settings/tokens/new
   - Check `repo` scope
   - Click "Generate token"
   - Copy the token

2. **Push Code:**
   ```bash
   cd C:\Users\Hp\Downloads\Selenium_Java
   git push -u origin main
   ```

3. **When prompted:**
   - Username: `subhashk2002`
   - Password: Paste your token

4. **Done!** Code is pushed to GitHub

---

## 🔄 Future Pushes (After Storing Token)

After storing token in Windows Credential Manager, future pushes are simple:

```bash
git add .
git commit -m "Your message"
git push origin main
```

No authentication prompts needed!

---

## 🆘 Troubleshooting

### Issue: "Authentication failed"
**Solution:** Make sure you're using correct token/credentials

### Issue: "remote: Repository not found"
**Solution:** Check URL spelling:
```bash
git remote -v
# Should show:
# origin  https://github.com/subhashk2002/Selenium_Framework.git
```

### Issue: "fatal: unable to access"
**Solution:** Check internet connection and try again

### Issue: Token not working
**Solution:** Regenerate token with `repo` scope selected

---

## 📋 Complete Push Command (Using Token)

```bash
cd C:\Users\Hp\Downloads\Selenium_Java
git push -u origin main
# Enter username: subhashk2002
# Enter password: YOUR_PERSONAL_ACCESS_TOKEN
```

---

## ✨ Next Steps

1. Generate Personal Access Token: https://github.com/settings/tokens/new
2. Copy the token (save it somewhere safe temporarily)
3. Run: `cd C:\Users\Hp\Downloads\Selenium_Java && git push -u origin main`
4. When prompted:
   - Username: `subhashk2002`
   - Password: Paste your token
5. Wait for push to complete
6. Verify on GitHub: https://github.com/subhashk2002/Selenium_Framework

---

## 🎯 Summary

**Recommended:** Use Personal Access Token (Option 1)
- Simpler setup
- No SSH key generation
- Just 3 steps: Generate → Paste → Push
- Takes ~2 minutes

**If you want long-term:** Use SSH Keys (Option 2)
- More secure
- No token to manage
- One-time setup
- Recommended for ongoing development
