-keepattributes LineNumberTable,SourceFile
-renamesourcefileattribute SourceFile
# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn aQute.bnd.annotation.spi.ServiceProvider
-dontwarn io.micrometer.context.ThreadLocalAccessor
-dontwarn jakarta.servlet.ServletContainerInitializer
-dontwarn java.lang.Module
-dontwarn java.lang.module.ModuleDescriptor
-dontwarn javax.xml.stream.XMLEventFactory
-dontwarn javax.xml.stream.XMLInputFactory
-dontwarn javax.xml.stream.XMLOutputFactory
-dontwarn javax.xml.stream.XMLResolver
-dontwarn javax.xml.stream.util.XMLEventAllocator
-dontwarn org.apiguardian.api.API
-dontwarn reactor.blockhound.integration.BlockHoundIntegration
-dontwarn org.xmlpull.v1.**
-dontwarn org.kobjects.**
-dontwarn org.ksoap2.**
-dontwarn org.kxml2.**
-dontwarn org.jdom2.**

-keep class one.empty3.apps.masks.** { *; }
-keep class one.empty3.** { *; }
-keep class com.formdev.** { *; }
-keep class org.kobjects.** { *; }
-keep class org.ksoap2.** { *; }
-keep class org.kxml2.** { *; }
-keep class org.xmlpull.** { *; }
-keep class org.jdom2.** { *; }
-keep class java.awt.** { *; }
-keep class javax.** { *; }
#-keep class one.empty3.** { *; }
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontoptimize
-dontpreverify
# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn com.google.api.AnnotationsProto
-dontwarn com.google.api.ClientProto
-dontwarn com.google.api.FieldBehaviorProto
-dontwarn com.google.api.ResourceProto
-dontwarn com.google.api.RoutingProto
-dontwarn com.google.protobuf.AbstractMessage$Builder
-dontwarn com.google.protobuf.AbstractMessage$BuilderParent
-dontwarn com.google.protobuf.AbstractMessage
-dontwarn com.google.protobuf.AbstractMessageLite$Builder
-dontwarn com.google.protobuf.AbstractParser
-dontwarn com.google.protobuf.ByteString
-dontwarn com.google.protobuf.Descriptors$Descriptor
-dontwarn com.google.protobuf.Descriptors$FileDescriptor
-dontwarn com.google.protobuf.Duration$Builder
-dontwarn com.google.protobuf.Duration
-dontwarn com.google.protobuf.DurationProto
-dontwarn com.google.protobuf.EmptyProto
-dontwarn com.google.protobuf.ExtensionRegistry
-dontwarn com.google.protobuf.FieldMaskProto
-dontwarn com.google.protobuf.GeneratedMessage$GeneratedExtension
-dontwarn com.google.protobuf.GeneratedMessageV3$Builder
-dontwarn com.google.protobuf.GeneratedMessageV3$BuilderParent
-dontwarn com.google.protobuf.GeneratedMessageV3$FieldAccessorTable
-dontwarn com.google.protobuf.GeneratedMessageV3
-dontwarn com.google.protobuf.Internal
-dontwarn com.google.protobuf.LazyStringArrayList
-dontwarn com.google.protobuf.LazyStringList
-dontwarn com.google.protobuf.MapEntry
-dontwarn com.google.protobuf.MapField
-dontwarn com.google.protobuf.Message
-dontwarn com.google.protobuf.MessageOrBuilder
-dontwarn com.google.protobuf.Parser
-dontwarn com.google.protobuf.ProtocolStringList
-dontwarn com.google.protobuf.RepeatedFieldBuilderV3
-dontwarn com.google.protobuf.SingleFieldBuilderV3
-dontwarn com.google.protobuf.Timestamp$Builder
-dontwarn com.google.protobuf.Timestamp
-dontwarn com.google.protobuf.TimestampProto
-dontwarn com.google.protobuf.UninitializedMessageException
-dontwarn com.google.protobuf.UnknownFieldSet
-dontwarn com.google.protobuf.WireFormat$FieldType
-dontwarn com.google.rpc.ErrorInfo
-dontwarn com.google.type.Date$Builder
-dontwarn com.google.type.Date
-dontwarn com.google.type.DateProto
-dontwarn com.google.type.Expr$Builder
-dontwarn com.google.type.Expr
-dontwarn com.google.type.ExprProto
-dontwarn java.awt.event.ActionEvent
-dontwarn java.awt.event.ActionListener
-dontwarn java.awt.event.ComponentEvent
-dontwarn java.awt.event.MouseEvent
-dontwarn java.awt.event.MouseListener
-dontwarn java.awt.event.MouseMotionListener
-dontwarn java.beans.BeanDescriptor
-dontwarn java.beans.BeanInfo
-dontwarn java.beans.Beans
-dontwarn java.beans.ExceptionListener
-dontwarn java.beans.Introspector
-dontwarn java.beans.PersistenceDelegate
-dontwarn java.beans.XMLDecoder
-dontwarn java.beans.XMLEncoder
-dontwarn javax.swing.AbstractAction
-dontwarn javax.swing.JButton
-dontwarn javax.swing.JFileChooser
-dontwarn javax.swing.JFrame
-dontwarn javax.swing.JLabel
-dontwarn javax.swing.JPanel
-dontwarn javax.swing.JScrollPane
-dontwarn javax.swing.JSplitPane
-dontwarn javax.swing.JTable
-dontwarn javax.swing.JTextArea
-dontwarn javax.swing.JTextField
-dontwarn javax.swing.JTextPane
-dontwarn javax.swing.Timer
-dontwarn javax.swing.text.DateFormatter
-dontwarn com.formdev.flatlaf.FlatDarkLaf