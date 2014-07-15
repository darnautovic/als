package com.als.shared.utils.crypto


import java.security.interfaces.{RSAPublicKey, RSAPrivateKey}
import java.security.spec.PKCS8EncodedKeySpec
import java.security._
import java.security.spec.X509EncodedKeySpec
import javax.crypto._
import org.apache.commons.codec.binary.Base64


object CryptoUtils {
  object rsa {

    def decodePrivateKey(encodedKey: Array[Byte]): PrivateKey = {
      val spec = new PKCS8EncodedKeySpec(encodedKey)
      val factory = KeyFactory.getInstance("RSA")
      factory.generatePrivate(spec)
    }

    def decodePublicKey(encodedKey: Array[Byte]): PublicKey = {
      val spec = new X509EncodedKeySpec(encodedKey)
      val factory = KeyFactory.getInstance("RSA")
      factory.generatePublic(spec)
    }

    def encrypt(key: PublicKey, data: Array[Byte]): Array[Byte] = {
      val cipher = Cipher.getInstance("RSA")
      cipher.init(Cipher.ENCRYPT_MODE, key)
      cipher.doFinal(data)
    }

    def decrypt(key: PrivateKey, data: Array[Byte]): Array[Byte] = {
      val cipher = Cipher.getInstance("RSA")
      cipher.init(Cipher.DECRYPT_MODE, key)
      cipher.doFinal(data)
    }

    def sign(key: PrivateKey, data: Array[Byte]): Array[Byte] = {
      val signer = Signature.getInstance("SHA1withRSA")
      signer.initSign(key)
      signer.update(data)
      signer.sign
    }

    def verify(key: PublicKey, signature: Array[Byte], data: Array[Byte]): Boolean = {
      val verifier = Signature.getInstance("SHA1withRSA")
      verifier.initVerify(key)
      verifier.update(data)
      verifier.verify(signature)
    }


    def encryptB64(key: PublicKey, data: Array[Byte]): String = {
      (new Base64()).encodeAsString(this.encrypt(key, data))
    }

    def encryptB64(key: PublicKey, data: String): String = {
      this.encryptB64(key, data.getBytes)
    }

    def encodedKeyToBase64Key(key :Array[Byte]) :String = {
      (new Base64()).encodeAsString(key)
    }

    def base64KeyToEncodedKey(key: String) :Array[Byte] =
    {
      (new Base64()).decode(key)
    }

    def generateKeyPair:(RSAPublicKey, RSAPrivateKey) = {
      val r = new scala.util.Random
      val keyGen: KeyPairGenerator  = KeyPairGenerator.getInstance("RSA")
      val random: SecureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN")
      keyGen.initialize(1024, random)
      val keyPair: KeyPair = keyGen.genKeyPair()
      val privateKey: RSAPrivateKey = keyPair.getPrivate.asInstanceOf[RSAPrivateKey]
      val publicKey: RSAPublicKey = keyPair.getPublic.asInstanceOf[RSAPublicKey]

      (publicKey, privateKey)
    }
  }
}